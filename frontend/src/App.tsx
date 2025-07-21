import { Depths, Stack, TextField, Toggle, TooltipHost } from '@fluentui/react';
import React from 'react';
import './App.css';
import CodeBlock from './components/CodeBlock';

const App: React.FC = () => {
  const [blocks, setBlocks] = React.useState(1);
  const [inputs, setInputs] = React.useState(0);
  const [executing, setExecuting] = React.useState(false);
  const [databaseName, setDatabaseName] = React.useState('');
  const [autoDisplayGraphs, setAutoDisplayGraphs] = React.useState(false);

  const updateExecutionState = React.useCallback((index: number, executing: boolean) => {
    setExecuting(executing);
    setInputs(inputs + 1);
    if (index === blocks - 1) {
      setBlocks(blocks + 1);
    }
  }, [blocks, inputs]);

  const onDatabaseNameChange = React.useCallback((_, value: string | undefined) => {
    setDatabaseName(value ?? '');
  }, []);

  const onToggleChange = React.useCallback((_, checked: boolean | undefined) => {
    setAutoDisplayGraphs(checked ?? false);
  }, []);

  return (
    <>
      <div className="header-container">
        <img src="/provexa-logo.png" alt="Provexa Logo" className="logo" />
        <h1 className="header-title">Provexa WebUI</h1>
      </div>
      <div className="database-container">
        <TextField
          label="📂 Database Name"
          value={databaseName}
          onChange={onDatabaseNameChange}
          placeholder="Enter database name"
          style={{ width: '300px', fontSize: '16px' }}
        />
        <div style={{ display: 'flex', alignItems: 'center', marginLeft: '20px' }}>
          <Toggle
            label="Auto Display Graphs"
            checked={autoDisplayGraphs}
            onChange={onToggleChange}
            inlineLabel
          />
          <TooltipHost
            content="Automatically appends 'display variable-name;' to your query if missing. No need to manually add the display command."
            styles={{ root: { marginLeft: '5px', marginBottom: '15px', cursor: 'pointer' } }}
          >
            <span style={{ fontSize: '16px', color: '#0078D4' }}>?</span>
          </TooltipHost>
        </div>
      </div>
      <div id="app" style={{ boxShadow: Depths.depth8 }}>
        <Stack tokens={{ childrenGap: 20 }}>
          {[...Array(blocks)].map((_, i) => (
            <CodeBlock
              index={i}
              inputs={inputs}
              executing={executing}
              updateExecutionState={updateExecutionState}
              databaseName={databaseName}
              autoDisplayGraphs={autoDisplayGraphs}
              key={i}
            />
          ))}
        </Stack>
      </div>
    </>
  );
};

export default App;
